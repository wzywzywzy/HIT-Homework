`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 20:05:59
// Design Name: 
// Module Name: main
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


module main(add1, add2, add3, add4, digCnf, type, reLock,clk, rst,  
            countdown, alarm, rest, an0, an1, pipe0, pipe1);
	
	input clk,rst,add1,add2,add3,add4,digCnf,type,reLock;
	
	output alarm;
	output [2:0] rest;
	output [7:0] countdown;
	
	output [3:0] an0;
	output [3:0] an1;
	output [7:0] pipe0;
	output [7:0] pipe1;

	wire add1;
	wire add2;
	wire add3;
	wire add4;
	wire [31:0] pw;
	wire [3:0] pw_num;
	wire inpFin;
	
	wire digCnf;
	wire type;

	reg [31:0] lc;
	reg [3:0] lc_num;

	reg inputScen;
	reg lockState;
	reg [1:0] failCnt;
	wire [1:0] restTry;

	reg enEnter;
	reg enAlarm;
	wire enFail;

	reg inpFin_d;
	reg type_d;
	reg reLock_d;

	initial begin
		lc <= 0;
		lc_num <= 4;
		enEnter <= 0;enAlarm <= 0;inpFin_d <= 0;
		type_d <= 0;reLock_d <= 0;
        
        failCnt <= 0;lockState <= 1;inputScen <= 0;	
	end

	password PW(.clk(clk),.enEnter(enEnter),.add1(add1),.add2(add2),.add3(add3),.add4(add4),
		.digCnf(digCnf),.type(type),.inputScen(inputScen),.lockState(lockState),.inpFin(inpFin),
		.pw(pw),.num(pw_num),.countdown(countdown));

	 light L (.clk(clk),.enAlarm(enAlarm),.restTry(restTry),.lockState(lockState),.alarm(alarm),.rest(rest));

	number NUM(.clk(clk),.an0(an0),.an1(an1),
	.pipe0(pipe0),.pipe1(pipe1),.enEnter(enEnter),
	.enFail(enFail),.lockState(lockState),.pw(pw),.num(pw_num));
	
	assign restTry = 3 - failCnt;
	assign enFail = (failCnt > 2) ? 1 : 0;
	
	always @(posedge clk or posedge rst) begin
		if (!rst) begin
			lc <= 0;
            lc_num <= 4;enEnter <= 0;enAlarm <= 0;inpFin_d <= 0;type_d <= 0;reLock_d <= 0;
            
            failCnt <= 0;lockState <= 1;inputScen <= 0;	
		end
		
		else if (lockState && !enEnter) begin
		    // 如果还未解锁
			if (!type_d && type && !enFail) begin
				//按完确认键后将状态置为输入密码
				enAlarm <= 0;inputScen <= 0;enEnter <= 1;
			end
		end
		
        else if (!lockState && !enEnter) begin
		    // 如果已经解锁
		    
			if (!reLock_d && reLock) begin
				lockState <= 1;//重新锁上
			end
			else if (!type_d && type) begin
				enEnter <= 1;
				inputScen <= 1;//设置密码
			end
		end
		else if (enEnter && !inpFin_d && inpFin) begin
			// 密码输入结束
			if (inputScen)  begin
				// 修改模式结束
				lc <= pw;
				lc_num <= pw_num;
				lockState <= 1;
				// 自动上锁
			end
			else 
			begin 
				// 解锁模式结束
				if (pw_num!=lc_num|| pw != lc || !countdown ) begin
					// 不够位数或者倒计时结束
					enAlarm <= 1;failCnt <= failCnt + 1;
				end
				else begin
					// 成功解锁
					lockState <= 0;failCnt <= 0;
				end
			end

			enEnter <= 0;
		end

		// 纪录之前状态
		type_d <= type;
		inpFin_d <= inpFin;
		reLock_d <= reLock;
		
	end

endmodule