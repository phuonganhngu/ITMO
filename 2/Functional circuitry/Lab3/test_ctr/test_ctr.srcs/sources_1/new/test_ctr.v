`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 17.04.2019 13:24:28
// Design Name: 
// Module Name: test_ctr
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module test_ctr(
    input clk,
    input rst,
    input sw,
    output reg [7:0] leds
);

reg [31:0] ctr;

always@(posedge clk)
    if(rst) begin
        leds <= 0;
        ctr  <= 0;
    end else begin
        if(sw) begin
            ctr <= ctr +1;
            if(ctr == 100000000) begin 
                leds <= leds + 1;
                ctr  <= 0;
            end
        end
    end
    
endmodule
