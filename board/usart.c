#include "usart.h"


void usart_init()
{
    /* seteaza baud rate la 9600 */
    UBRR0H = 0;
    UBRR0L = 103;

    /* porneste transmitatorul */
    UCSR0B = (1<<TXEN0) | (1<<RXEN0);

    /* seteaza formatul frame-ului: 8 biti de date, 1 bit de stop, fara paritate */
    UCSR0C = (3<<UCSZ00) | (0<<USBS0) | (0<<UPM00);
}

/*
 * Functie ce primeste un caracter prin USART
 *
 * @return - caracterul primit
 */
unsigned char usart_data_receive( void )
{
    /* asteapta cat timp bufferul e gol */
    while(!(UCSR0A & (1<<RXC0)));

    /* returneaza datele din buffer */
    return UDR0;
}