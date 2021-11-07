module mult_tb;
    reg [7:0] a_in, b_in;
    reg clk_in, rst_in, start_in;
    wire busy_out;
    wire [15:0] y_out;
    

    mult mult1 (
        .clk(clk_in),
        .rst(rst_in),
        .a_in(a_in),
        .b_in(b_in),
        
        .start(start_in),
        .busy(busy_out),
        .y_out(y_out)
    );
    
    integer a = 5, b = 3;
    
    always #5 clk_in = ~clk_in;
    
    initial begin
        clk_in = 0;
        rst_in = 1;
        #10
        rst_in = 0;
        a_in = a;
        b_in = b;
        start_in = 1;
                
        #10
        if (busy_out == 0) $stop;
        $display ("a_in=%b , b_in=%b, y_out=%b", a_in , b_in, y_out);   
    end
endmodule