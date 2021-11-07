module cube_root(
    input clk,
    input rst,
    input [7:0] x_in,

    output reg [7:0] y_out,
    output reg ready_out   
);

    reg start_mult;
    wire busy_mult;

    reg [7:0] a_mult, b_mult;
    wire [15:0] y_mult;
    
    mult mult1 (
        .clk(clk),
        .rst(rst),
        .a_in(a_mult),
        .b_in(b_mult),
        
        .start(start_mult),
        .busy(busy_mult),
        .y_out(y_mult)
    );
    
    reg [15:0] a_sum, b_sum;
    wire [15:0] y_sum;
    
    sum sum1 (
        .clk(clk),
        .a_in(a_sum),
        .b_in(b_sum),
        
        .y_out(y_sum)
    );
    
    reg [15:0] a_diff, b_diff;
    wire signed [15:0] y_diff;
    
    diff diff1 (
        .clk(clk),
        .a_in(a_diff),
        .b_in(b_diff),
        
        .y_out(y_diff)
    );

    localparam COUNT_2Y = 5'b00000;
    localparam COUNT_3Y = 5'b00001;
    localparam AWAIT_2Y = 5'b00010;
    localparam AWAIT_3Y = 5'b00011;
    localparam COUNT_B = 5'b00100;
    localparam AWAIT_B = 5'b00101;
    localparam CHECK = 5'b00110;
    localparam ITERATE = 5'b00111;
    localparam SUM1 = 5'b01000;
    localparam AWAIT_SUM1 = 5'b01001;
    localparam SUM2 = 5'b01010;
    localparam AWAIT_SUM2 = 5'b01011;
    localparam SUM3 = 5'b01100;
    localparam AWAIT_SUM3 = 5'b01101;
    localparam DIFF = 5'b01110;
    localparam AWAIT_ITERATE = 5'b01111;
    localparam AWAIT_DIFF = 5'b10000;

    
    reg [4:0] state;
    
    reg [15:0] y_tmp;
    reg [7:0] x_tmp;
    reg x_been_read;
    reg [10:0] b;
    reg signed [15:0] s;
               
    
    always@( posedge clk )
        if ( rst ) begin
            s = 16'd9;
            x_been_read = 0;
            y_tmp = 0;
            b = 0;
            state = COUNT_2Y;
            y_out = 0;
            ready_out = 0;
        end else begin
            case (state)
                    AWAIT_2Y:
                        if (!busy_mult) begin
                            state = COUNT_2Y;
                        end
                    COUNT_2Y:
                        if (!busy_mult) begin
                            if (start_mult) begin
                                y_tmp = y_mult;
                                start_mult = 0;
                                state = COUNT_3Y;
                            end else begin
                                a_mult = y_tmp;
                                b_mult = 2;
                                state = AWAIT_2Y;
                                start_mult = 1;
                            end
                        end
                    AWAIT_3Y:
                        if (!busy_mult) begin
                            state = COUNT_3Y;
                        end
                    COUNT_3Y:
                        if (!busy_mult) begin
                            if (start_mult) begin
                                b = y_mult;
                                start_mult = 0;
                                state = COUNT_B;
                            end else begin
                                a_mult = y_tmp;
                                b_mult = 3;
                                state = AWAIT_3Y;
                                start_mult = 1;
                            end
                        end
                    AWAIT_B:
                        if (!busy_mult) begin
                            state = COUNT_B;
                        end
                        
                    COUNT_B:
                        if (!busy_mult) begin
                            if (start_mult) begin
                                a_sum = y_mult;
                                b_sum = 1;
                                state = AWAIT_SUM2;
                            end else begin
                                a_sum = y_tmp;
                                b_sum = 1;
                                state = AWAIT_SUM1;
                            end
                        end
                        
                    AWAIT_SUM1:
                        begin
                            state = SUM1;
                        end
                    SUM1:
                        begin
                            a_mult = b;
                            b_mult = y_sum;
                            state = AWAIT_B;
                            start_mult = 1;
                        end
                    AWAIT_SUM2:
                        begin
                            state = SUM2;
                        end
                    SUM2:
                        begin
                            b = y_sum;
                            b = b << s;
                            start_mult = 0;
                            state = CHECK;
                        end
                        
                   
                    CHECK:
                        begin
                            if (!x_been_read) begin
                                x_tmp = x_in;
                                x_been_read = 1;
                            end
                            if (x_tmp >= b) begin
                                a_diff = x_tmp;
                                b_diff = b;
                                state = AWAIT_DIFF;
                            end else begin
                                a_diff = s;
                                b_diff = 3;
                                state = AWAIT_ITERATE;
                            end
                        end
                        
                    AWAIT_DIFF:
                        begin
                            state = DIFF;
                        end
                    DIFF:
                        begin
                            x_tmp = y_diff;
                            
                            a_sum = y_tmp;
                            b_sum = 1;
                            state = AWAIT_SUM3;
                        end
                        
                    AWAIT_SUM3:
                        begin
                            state = SUM3;
                        end
                    SUM3:
                        begin
                            y_tmp = y_sum;
                            a_diff = s;
                            b_diff = 3;
                            state = AWAIT_ITERATE;
                        end
                        
                    AWAIT_ITERATE:
                        begin
                            state = ITERATE;
                        end   
                    ITERATE:
                        begin
                            s = y_diff;
                            if (s > -3) begin
                                state = COUNT_2Y;
                            end else  begin
                                y_out = y_tmp;
                                ready_out = 1;
                            end
                        end
            endcase
        end
endmodule
