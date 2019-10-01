#include <avr/io.h>
#include <avr/interrupt.h>

#include <util/delay.h>

#include <stdbool.h>
#include <stdio.h>
#include <string.h>

#include "utils.h"

static int current_song = 1;
static bool playing = false;
/* UDR0 */

#define PLAY 'A'
#define PAUSE 'B'
#define NEXT_SONG 'C'
#define PREVIOUS_SONG 'D'
#define MAX_SONGS 7

static FATFS fs;

ISR (USART0_RX_vect) {
    char received = UDR0;

    switch(received) {
        case PLAY:
            playing = true;
            PORTD ^= (1 << PD7);
            set_playing(true);
            _delay_ms(500);
            play_song(&current_song);
            break;
        case PAUSE:
            PORTD ^= (1 << PD7);
            _delay_ms(500);
            playing = false;
            set_playing(false);
            break;
        case NEXT_SONG:
            PORTD ^= (1 << PD7);
            current_song++;
            _delay_ms(500);
            if (current_song == MAX_SONGS) {
                current_song = 1;
            }
            play_song(&current_song);
            break;
        case PREVIOUS_SONG:
            PORTD ^= (1 << PD7);
            _delay_ms(500);
            current_song--;
            if (!current_song) {
                current_song = 6;
            }
            play_song(&current_song);
            break;
    }
}

int main(void)
{
    IO_init();
    timer2_init();
    init_usart();

    sei();

    DDRD |= (1 << PD7);
    PORTD |= (1 << PD7);
    for (;;) {

        if(pf_mount(&fs) != FR_OK)
        {
            // wait a while and retry
            _delay_ms(1000);
            // continue;
        }

        _delay_ms(1000);
        for(;;) {

            if (playing) {
                play_song(&current_song);
            }
        }
    }

    return 0;
}
