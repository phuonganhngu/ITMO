module cube_root_tb;
    reg [7:0] x_in;
    reg clk_in, rst_in;
    wire [7:0] y_out;
    wire ready_out;
    
    cube_root cr1 (
        .clk(clk_in),
        .rst(rst_in),
        .x_in(x_in),

        .y_out(y_out),
        .ready_out(ready_out)
    );
    
    integer x = 9;
    
    always #5 clk_in = ~clk_in;
    
    initial begin
        clk_in = 0;
        rst_in = 1;
        #10
        rst_in = 0;
        x_in = 0;
        @(posedge ready_out) begin
            $display ("IN: x = %d, OUT: y = %d", x_in, y_out);
        end
        
        while (x < 36)
            begin 
                clk_in = 0;
                rst_in = 1;
                #10
                rst_in = 0;
                x_in = x;
                @(posedge ready_out) begin
                    $display ("IN: x = %d, OUT: y = %d", x_in, y_out);
                end
                x = x + 3;
            end
        
        clk_in = 0;
        rst_in = 1;
        #10
        rst_in = 0;
        x_in = 255;
        @(posedge ready_out) begin
            $display ("IN: x = %d, OUT: y = %d", x_in, y_out);
        end
            
        $stop;       
    end
    
    /*initial begin
        clk_in = 0;
        rst_in = 1;
        #10
        rst_in = 0;
        x_in = x;
                
        #3000
        $display ("y_out=%b", y_out);
        $stop;       
    end*/
endmodule