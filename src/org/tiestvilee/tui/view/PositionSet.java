package org.tiestvilee.tui.view;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.tiestvilee.tui.primitives.Position;

public class PositionSet {

	private final Set<Position> dirt = new HashSet<Position>();
	private final Semaphore dirtLock = new Semaphore(1);

	public void putPosition(Position position) {
		try {
			// should do this as block
			acquireDirtLock();
			dirt.add(position);
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted!", e);
		} finally {
			releaseDirtLock();
		}
	}
	
	public Set<Position> getPositionsAndClearThem() {
		Set<Position> result;
		try {
			acquireDirtLock();
			result = new HashSet<Position>(dirt);
			dirt.clear();
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted!", e);
		} finally {
			releaseDirtLock();
		}
		return result;
	}

	private void acquireDirtLock() throws InterruptedException {
		dirtLock.acquire();
	}

	private void releaseDirtLock() {
		dirtLock.release();
	}

}
