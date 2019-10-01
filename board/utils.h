#ifndef UTILS_H
#define UTILS_H

#include "pff.h"

void IO_init(void);
void timer0_start(void);
void timer0_stop(void);
void timer1_start(void);
void timer1_stop(void);
void set_playing(bool new_value);
bool continue_play();
DWORD load_header(void);
UINT play(const char *path);
void timer2_init(void);
void get_music(int n, const char *folder, char *filename);
void play_song(int *current_song);
void init_usart(void);

#endif