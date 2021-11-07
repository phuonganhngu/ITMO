module diff(
    input clk,
    input [15:0] a_in,
    input [15:0] b_in,
    
    output reg signed [15:0] y_out
 );

    always@ (posedge clk)
        begin
            y_out = a_in - b_in;
        end   
endmodule