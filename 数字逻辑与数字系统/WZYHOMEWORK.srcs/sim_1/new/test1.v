`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2020/12/16 21:38:06
// Design Name: 
// Module Name: test1
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


module test1();

    reg clk;
	reg rst;
	reg add1;
	reg add2;
	reg add3;
	reg add4;
	reg digCnf;
	reg type;
	reg reLock;
	wire [7:0] countdown;
	wire [3:0] blue;
	wire [3:0] green;
	wire [3:0] red;
	wire [3:0] an0;
	wire [3:0] an1;
	wire [7:0] pipe0;
	wire [7:0] pipe1;
	
	wire enEnter;
	wire lockState;
	wire inpFin;
	wire [31:0] pw;
	wire [2:0] pw_num;
	// wire scaleTime;
	// wire scaleTime_d;
	wire [3:0] id0;
	wire [3:0] id1;
	
	main u0( .add1(add1), .add2(add2),.add3(add3),.add4(add4),.digCnf(digCnf),.type(type),.reLock(reLock), 
        .clk(clk), .rst(rst), .countdown(countdown), .an0(an0), 
        .an1(an1),  .pipe0(pipe0), .pipe1(pipe1));
	
	reg op;
	
	initial begin

        clk = 0;
        rst = 0;
        op = 0;
        type = 0;
        add1 = 0;
        add2 = 0;
		add3 = 0;
		add4 = 0;
        digCnf = 0;
        reLock = 0;
	end
	
	always #1   clk = ~clk;
    always #100000000 op = ~op;
    
    always @ (op) begin
        #10 type <= 1;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 digCnf <= ~digCnf;
        #10 type <= 0;
        
        #5 digCnf = 0;
        
        #10 type <= 1;
        #5 add1 <= 1; #5 add1 <= 0;
        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        
        #5 add1 <= 1; #5 add1 <= 0;
        #5 add3 <= 1; #5 add3 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        
        #5 add4 <= 1; #5 add4 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        
        #5 type <= 0;
        
        #5 type <= 1;
        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        #5 type <= 0;

        #40 type <= 1;
        #5 add1 <= 1; #5 add1 <= 0;

        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add3 <= 1; #5 add3 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add3 <= 1; #5 add3 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;
        #5 type <= 0;

        #20 reLock <= 1;

        #20 type <= 1;

        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add2 <= 1; #5 add2 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

        #5 add1 <= 1; #5 add1 <= 0;
        #5 digCnf <= 1; #5 digCnf <= 0;

    end

endmodule
