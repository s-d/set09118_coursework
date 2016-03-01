#ifndef REMOTE_h
#define REMOTE_h

#include "HID.h"

#if !defined(_USING_HID)

#warning "Using legacy HID core (non pluggable)"

#else
//================================================================================
//================================================================================
//	Remote 
 
#define REMOTE_CLEAR 0
#define VOLUME_UP 1
#define VOLUME_DOWN 2
#define VOLUME_MUTE 4
#define REMOTE_PLAY 8
#define REMOTE_PAUSE 16
#define REMOTE_STOP 32
#define REMOTE_NEXT 64
#define REMOTE_PREVIOUS 128
#define REMOTE_FAST_FORWARD 256
#define REMOTE_REWIND 512
 
class Remote_
{
private:
public:
	Remote_(void);
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
extern Remote_ Remote;

#endif
#endif