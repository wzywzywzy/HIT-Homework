`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 20:44:05
// Design Name: 
// Module Name: light
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
module light(clk, enAlarm, restTry, lockState, alarm, rest);
	reg [24:0] twinkleCnt;

	input clk;
	input enAlarm;
	input [1:0] restTry;
	input lockState;
	output alarm;
	output [2:0] rest;

	reg alarm;
	reg [2:0] rest;
    
	initial begin
		alarm <= 0;
		rest <= 0;
		twinkleCnt <= 1;
	end
	
	always @(posedge clk) begin
	    if (!lockState) begin
	        rest <= 0;
	    end
	    else begin
	        case (restTry)
                3: rest <= 3'b111;
                2: rest <= 3'b011;
                1: rest <= 3'b001;
                0: rest <= 3'b000;
                default: rest <= 3'b111;
            endcase
        end
	end

	always @(posedge clk) begin
	    if (enAlarm && !twinkleCnt) begin
	        alarm <= ~ alarm;
	    end
	    
		if (!lockState) begin
			alarm <= 0;
		end
		else if (!enAlarm) begin
			alarm <= 1;
		end
		
		twinkleCnt <= twinkleCnt + 1'b1;
		
	end

endmodule
