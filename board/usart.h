#ifndef USART_H_
#define USART_H_

#include <avr/io.h>
#include <stdio.h>
#include <util/delay.h>

/*The function is declared to initialize the USART with following cinfiguration:-
USART mode - Asynchronous
Baud rate - 9600
Data bits - 8
Stop bit - 1
Parity - No parity.*/
void usart_init();

/*The function is declared to receive data.*/
unsigned char usart_data_receive( void );

#endif // USART_H_
