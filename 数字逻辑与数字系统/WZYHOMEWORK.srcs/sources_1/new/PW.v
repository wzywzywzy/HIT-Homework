`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 20:44:05
// Design Name: 
// Module Name: password
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
module password(clk, enEnter, add1, add2, add3, add4, digCnf, type, inputScen, lockState,
               pw, num, countdown, inpFin);
               
    reg	[27:0] extinguishCnt;
    
    input clk,add1,add2,add3,add4,digCnf,type,enEnter,inputScen,lockState;
	
	output [31:0] pw;
	output [7:0] countdown;
	output [3:0] num;
	output inpFin;
	
	reg enEnter_d;
	reg digCnf_d;
	reg	add1_d;
	reg	add2_d;
	reg add3_d;
	reg add4_d;
   	reg type_d;
    reg [3:0] num;
	reg [2:0] rest;
	reg [31:0] pw;
    reg [7:0] countdown;
	reg inpFin;
	
	initial begin
		rest <= 7;
		num <= 1'b1;
		pw <= 0;
		inpFin <= 1'b0;
		countdown <= 8'b11111111;
		extinguishCnt <= 1'b1;
		
		enEnter_d <= 0;
		digCnf_d <= 0;
		add1_d <= 0;
		add2_d <= 0;
		add3_d <= 0;
		add4_d <= 0;
		type_d <= 0;
	end
    
	always @(posedge clk) begin 
        
		if ( !enEnter_d  && enEnter ) begin
			rest <= 7;
			num <= 1;
			pw <= 0;
			inpFin <= 0;
			countdown <= 8'b11111111;
			extinguishCnt <= 1;
		end//初始化
		else begin
			extinguishCnt <= extinguishCnt + 1'b1;
		end
    
		if (enEnter) begin
			if ( (type_d && !type && ((num > 4 && inputScen == 1) || inputScen == 0)) || (inputScen == 0 && !countdown && !inpFin) || 
                         (num > 8) ) begin
                        // 到时间或者长度达到八位产生溢出
                        num <= num - 1'b1;
                        inpFin <= 1;
                    end
			else if (!add1_d && add1) begin
                pw <= pw + (1 << (4 * (num - 1)));
			end
			else if (!add2_d && add2) begin
				pw <= pw + (2 << (4 * (num - 1)));
                        
			end 
			else if (!add3_d && add3) begin
                pw <= pw + (4 << (4 * (num - 1)));
            end
            else if (!add4_d && add4) begin
                pw <= pw + (8 << (4 * (num - 1)));
            end  
			else if (!digCnf_d && digCnf) begin
				num <= num + 1'b1;//确认键后位数加一
			end
			
			if ( !extinguishCnt && !inputScen ) begin
				// 灭掉一盏计时用灯
				countdown[rest] <= 0;
				rest <= rest - 1;
			end
			//将前几位灯按时间灭掉
		end
		
		else begin  // enEnter = 0
		    if ( lockState )begin
		     countdown <= 8'b10011001 ;
		    end
		    else begin
		    countdown <= 8'b11111111;
		    end
		end//上锁时和开锁时灯的状态
    
        enEnter_d <= enEnter;
		digCnf_d <= digCnf;
		add1_d <= add1;
		add2_d <= add2;
		add3_d <= add3;
		add4_d <= add4;
		type_d <= type;
	end
           
endmodule
