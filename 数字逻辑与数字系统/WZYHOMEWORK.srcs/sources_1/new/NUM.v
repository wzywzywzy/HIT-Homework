`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 20:44:05
// Design Name: 
// Module Name: number
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

module number(clk,  an0, an1, pipe0, pipe1, enEnter, enFail, lockState, pw, num,);

	input clk,enEnter,enFail,lockState;
	input [31:0] pw;
	input [3:0] num;
	output [3:0] an0;
	output [3:0] an1;
	output [7:0] pipe0;
	output [7:0] pipe1;

    reg [17:0] refreshCnt;
	wire [15:0] pw0;
	wire [15:0] pw1;
	wire [3:0] dig0;
	wire [3:0] dig1;
	wire [3:0] an0;
	wire [3:0] an1;
	reg [4:0] id0;
	reg [4:0] id1;
	reg [1:0] cur;

	wire [7:0] pipe0;
	wire [7:0] pipe1;
	
	wire needTwink0;
	wire needTwink1;
	wire [1:0] curRev;
	
	assign curRev = 3 - cur;
    
    assign needTwink0 = (enEnter && num - 1 == curRev + 4) ? 1: 0;
    assign needTwink1 = (enEnter && num - 1 == curRev) ? 1 : 0;
    
	perNumber PNUM0(.clk(clk),.enEnter(enEnter),.needTwink(needTwink0),.knum(id0),.pipe(pipe0));

	perNumber PNUM1(.clk(clk),.enEnter(enEnter),.needTwink(needTwink1),.knum(id1),.pipe(pipe1));

	initial begin
		id0 <= 15;id1 <= 15;
		cur <= 0;
		refreshCnt <= 1;
	end

	assign pw0 = pw[15:0];
	assign pw1 = pw[31:16];

	assign dig0 = ((pw1 % (1 << ((curRev + 1) * 4))) >> (curRev * 4));
	assign dig1 = ((pw0 % (1 << ((curRev + 1) * 4))) >> (curRev * 4));
	assign an0 = (1 << cur);
	assign an1 = (1 << cur);
	always @(posedge clk) begin
		
		if (!enEnter) begin
		
		    if (enFail) begin
		        case (an0)
		            4'b0001: id0 <= 11;
		            4'b0010: id0 <= 11;
		            4'b0100: id0 <= 12;
		            4'b1000: id0 <= 5;
		            default: id0 <= 15;
		        endcase
		        
		        case (an1)
		            4'b0001: id1 <= 0;
		            4'b0010: id1 <= 13;
		            4'b0100: id1 <= 11;
		            4'b1000: id1 <= 11;
		            default: id0 <= 15;
		        endcase
		    end 
		    
		    else if (lockState) begin
		          if (an0 == 4'b1000)begin
		              id0 = 14;
		          end
		          else begin
		          id0 = 11;
		          end
		          
		          if (an1 == 4'b0001)begin
                      id1 = 0;
                  end
                  else begin
                  id1 = 11;
                  end
			end
			
			else begin
			
			    if (an0 == 4'b1000)begin
                id0 = 17;
                end
                else begin
                id0 = 11;
                end
            
                if (an1 == 4'b0001)begin
                id1 = 1;
                end
                else begin
                id1 = 11;
                end
                
			end
			
		end
		
		else begin
		
			if (curRev + 4 > num - 1 && curRev <= num - 1) begin
				id0 <= 10;
				id1 <= dig1;
			end
            
			else if (curRev > num - 1) begin
				id0 <= 10;
				id1 <= 10;
			end
            
			else begin
				id0 <= dig0;
				id1 <= dig1;
			end
			
		end
		
		refreshCnt <= refreshCnt + 1;
		if (refreshCnt == 0) begin
		    cur <= cur + 1;
		end
		
	end

endmodule

