module mult(
    input clk,
    input rst,
    input [7:0] a_in,
    input [7:0] b_in,
    
    input start,
    output reg busy,
    output reg [15:0] y_out
 );
 
    localparam IDLE = 2'b00;
    localparam WORK = 2'b01;
    localparam AWAIT = 2'b10;
    
    reg  [2:0]  ctr;
    wire [2:0]  end_step;
    wire [7:0]  part_sum;
    wire [15:0] shifted_part_sum;
    reg [7:0]   a, b;
    
    reg [15:0]  part_res;
    reg [1:0] state;
    
    assign part_sum = a & {8{b[ ctr ] } } ;
    assign shifted_part_sum = part_sum << ctr ;
    assign end_step = (ctr == 3'h7);
//    assign busy = state;
    
    always@ (posedge clk)
        if ( rst ) begin
            ctr = 0;
            part_res = 0;
            y_out = 0;

            state = IDLE;
            busy = state;
        end else begin
            case ( state )
                AWAIT:
                    if (!start) begin
                        state = IDLE;
                    end
                IDLE:
                    if ( start ) begin
                        state = WORK;
                        busy = state;
                        a = a_in;
                        b = b_in;
                        ctr = 0;
                        part_res = 0;
                    end
                WORK:
                    begin
                        if ( end_step ) begin
                            state = AWAIT;
                            busy = 0;
                            y_out = part_res;
                        end
                        
                        part_res = part_res + shifted_part_sum;
                        ctr = ctr + 1;
                    end
            endcase
        end   
endmodule