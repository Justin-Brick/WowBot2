package com.estrelsteel.wowbot.command.audio;

import com.estrelsteel.wowbot.WowChannel;
import com.estrelsteel.wowbot.audio.VoiceHelper;
import com.estrelsteel.wowbot.audio.WowAudioCore;
import com.estrelsteel.wowbot.command.Command;
import com.estrelsteel.wowbot.command.CommandWrapper;
import com.estrelsteel.wowbot.user.WowUser;

public class Pause implements Command {

	private WowAudioCore wac;

	public Pause(WowAudioCore wac) {
		this.wac = wac;
	}

	@Override
	public boolean hasPermission(WowUser user) {
		// TODO: Add permissions
		return true;
	}

	@Override
	public boolean isValid(CommandWrapper wrapper) {
		if (!wrapper.getChannel().isRespondableChannel())
			return false;
		if (wrapper.getArguments().length <= 0)
			return false;
		return true;
	}

	@Override
	public boolean execute(CommandWrapper wrapper) {
		wrapper.deleteCalledMessage();
		WowChannel vc = VoiceHelper.determineChannel(wrapper);
		if (vc == null || !vc.isVoiceChannel()) {
			wrapper.getChannel().sendMessageDeleteAfter("You need to be in a voice channel to use this command.", 5000);
			return false;
		}
		if(wac.getQueue().getPlayer().isPaused()) {
			wrapper.getChannel().sendMessageDeleteAfter("Resuming the audio track.", 5000);
			wac.getQueue().getPlayer().setPaused(false);
		}
		else {
			wrapper.getChannel().sendMessageDeleteAfter("Pausing the audio track.", 5000);
			wac.getQueue().getPlayer().setPaused(true);
		}
		return true;
	}

	@Override
	public int getId() {
		return 1050;
	}

	@Override
	public String help() {
		return "**Description:** pauses/resumes the audio track.";
	}

	@Override
	public String usage() {
		return "**Usage**: " + this + "";
	}

	@Override
	public String toString() {
		return "pause";
	}

}
