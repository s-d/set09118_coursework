#ifndef MEDIA_h
#define MEDIA_h

#include "HID.h"

#if !defined(_USING_HID)

#warning "Using legacy HID core (non pluggable)"

#else
//================================================================================
//================================================================================
//	Media 
 
#define MEDIA_CLEAR 0
#define VOLUME_UP 1
#define VOLUME_DOWN 2
#define VOLUME_MUTE 4
#define MEDIA_PLAY 8
#define MEDIA_PAUSE 16
#define MEDIA_STOP 32
#define MEDIA_NEXT 64
#define MEDIA_PREVIOUS 128
#define MEDIA_FAST_FORWARD 256
#define MEDIA_REWIND 512
 
class Media_
{
private:
public:
	Media_(void);
	void begin(void);
	void end(void);
 
	// Volume
	void increase(void);
	void decrease(void);
	void mute(void);
 
	// Playback
	void play(void);
	void pause(void);
	void stop(void);
 
	// Track Controls
	void next(void);
	void previous(void);
	void forward(void);
	void rewind(void);
	 
	// Send an empty report to prevent repeated actions
	void clear(void);
};
extern Media_ Media;

#endif
#endif