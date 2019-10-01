PORT ?= /dev/ttyUSB0

all: bluetooth_speaker.hex

bluetooth_speaker.elf: bluetooth_speaker.c utils.c pff.c sd.c spi.c
	avr-g++ -Wall -Wextra -mmcu=atmega324a -DF_CPU=16000000 -Os -o $@ $^

%.o: %.c
	avr-g++ -Wall -Wextra -mmcu=atmega324a -DF_CPU=16000000 -Os -o $@ -c $^ 

bluetooth_speaker.hex: bluetooth_speaker.elf
	avr-objcopy -j .text -j .data -O ihex $^ $@
	avr-size $^

clean:
	rm -rf bluetooth_speaker.hex bluetooth_speaker.elf

upload: bluetooth_speaker.hex
	avrdude -c arduino -P $(PORT) -b 57600 -p atmega324p -U flash:w:$<:a

.PHONY: all clean upload
