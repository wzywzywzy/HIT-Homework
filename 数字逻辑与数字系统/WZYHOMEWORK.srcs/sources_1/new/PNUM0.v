`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 21:34:16
// Design Name: 
// Module Name: perNumber
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

module perNumber(clk, enEnter, needTwink, knum, pipe);

    parameter digitTwinkCntRange = 26;
    reg [digitTwinkCntRange:0] digitTwinkCnt;

	input clk;
	input enEnter;
	input needTwink;
	input [4:0] knum;
	output [7:0] pipe;

	reg [7:0] pipe;
	
	initial begin
		pipe <= 8'b00000000;
		digitTwinkCnt <= 0;
	end

	always @(posedge clk) begin
	
        if (needTwink && (digitTwinkCnt & (1 << (digitTwinkCntRange - 1)))) begin
            pipe <= 8'b00000000;
        end
        
        else begin
            if ((knum >= 0 && knum <= 9 && knum != 1 && knum != 4) || knum == 12 || knum >= 16)
                pipe[0] <= 1;// 0 2 3 5 6 7 8 9 12 16 17
            else
                pipe[0] <= 0;
    
            if (knum <= 4 || (knum >= 7 && knum <= 9) || knum == 14 || knum == 17)
                pipe[1] <= 1;// 0 1 2 3 4 7 8 9 14 17
            else
                pipe[1] <= 0;
    
            if (knum <= 1 || (knum >= 3 && knum <= 9) || knum == 14 || knum == 17)
                pipe[2] <= 1;// 0 1 3 4 5 6 7 8 9 14 17
            else
                pipe[2] <= 0;
    
            if ((knum <= 6 && knum >= 0 && knum != 1 && knum !=4) || (knum >= 8 && knum <= 14 && knum != 11))
                pipe[3] <= 1;// 0 2 3 5 6  8 9 10 12 13 14
            else
                pipe[3] <= 0;
    
            if (knum == 0 || knum == 2 || knum == 6 || knum == 8 ||  (knum >= 12 && knum <= 17 && knum !=15))
                pipe[4] <= 1;// 0 2 6 8 12 13 14 16 17
            else
                pipe[4] <= 0;
    
            if (knum == 0 || (knum >= 4 && knum <= 9 && knum != 7) || (knum >= 12 && knum <= 17 && knum != 15))
                pipe[5] <= 1;// 0 4 5 6 8 9 12 13 14 16 17
            else
                pipe[5] <= 0;
    
            if ((knum >= 2 && knum <= 12 && knum != 10 && knum !=7) || knum == 16)
                pipe[6] <= 1;// 2 3 4 5 6 8 9 11 12 16
            else
                pipe[6] <= 0;
    
            pipe[7] <= 0;//小数点恒为0 
        end
        
        digitTwinkCnt <= digitTwinkCnt + 1'b1;
	end

endmodule

