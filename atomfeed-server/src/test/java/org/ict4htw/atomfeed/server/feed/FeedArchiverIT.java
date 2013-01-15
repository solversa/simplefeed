package org.ict4htw.atomfeed.server.feed;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.ict4htw.atomfeed.SpringIntegrationIT;
import org.ict4htw.atomfeed.server.domain.EventArchive;
import org.ict4htw.atomfeed.server.domain.EventRecord;
import org.ict4htw.atomfeed.server.repository.AllEventRecords;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FeedArchiverIT extends SpringIntegrationIT {

	@Autowired
	private AllEventRecords allEventRecords;

	@Before
	@After
	public void purgeEventRecords() {
	    template.deleteAll(template.loadAll(EventRecord.class));
	    template.deleteAll(template.loadAll(EventArchive.class));
	}
	
	private final int ENTRIES_PER_FEED = 5;
	
	@Test
	public void shouldArchiveFeeds() throws URISyntaxException {
		for (int i= 1; i <= 7; i++) {
			String title = "Event" + i;
			allEventRecords.add(new EventRecord(UUID.randomUUID().toString(), title, new URI("http://uri/"+title), null));
		}
		int unarchivedCount = allEventRecords.getUnarchivedEventsCount();
		assertTrue("unarchived events should be 7.", unarchivedCount == 7);
		FeedArchiver feedArchiver = new FeedArchiver(allEventRecords);
		feedArchiver.archiveFeeds();
		int unarchivedEventsCount = allEventRecords.getUnarchivedEventsCount();
		assertTrue("unarchived events should be 2.", unarchivedEventsCount == 2);
	}
	
	
	
	
}
