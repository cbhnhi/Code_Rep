;********************************************************************
;
; Author : Shlomo Engelberg
;
; Date : 31 August 2006
; Revised : 31 May 2011
;
; File : ExtInt0.asm
;
; Hardware : Any 8052 based MicroConverter (ADuC8xx)
;
; Description : Toggles the LED each time EX0 is asserted.
;
LED EQU P3.4 ; P3.4 is red LED on eval board
;********************************************************************
CSEG AT 0000H
JMP MAIN ; Upon “power up” have the processor jump to
 ; the label MAIN.
CSEG AT 0003H ; The Ext. Int. 0 ISR
CPL LED ; Toggle the LED which is connected to P3.4.
RETI
CSEG AT 0100H
MAIN:
SETB IT0 ; Make the interrupt edge triggered.
SETB P3.2 ; Open up the “switch” in P3.2.
SETB EA ; Enable interrupts.
SETB EX0 ; Enable ext. int. 0.
JMP $
END