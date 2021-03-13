package it.akademija.journal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.user.UserDAO;

@Service
public class JournalService {

	@Autowired
	private JournalEntryDAO journalEntryDAO;

	@Autowired
	private UserDAO userDAO;

	@Transactional(readOnly = true)
	public Page<JournalEntry> getAllJournalEntries(Pageable pageable) {
		return journalEntryDAO.getAllJournalEntries(pageable);
	}

	@Transactional
	public void newJournalEntry(OperationType operationType, Long objectID, ObjectType objectType,
			String entryMessage) {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Long currentUserID = userDAO.findByUsername(currentUsername).getUserId();

		JournalEntry entry = new JournalEntry(currentUserID, currentUsername, LocalDateTime.now(), operationType,
				objectID, objectType, entryMessage);

		journalEntryDAO.saveAndFlush(entry);
	}

	/**
	 * GDPR related method to remove username from system logs after User asks
	 * to delete their data.
	 * 
	 * @param username
	 */
	public void depersonalizeUserLogs(String username) {
		List<JournalEntry> userJournalEntries= journalEntryDAO.findByUserName(username);
		
		for(JournalEntry entry: userJournalEntries) {
			entry.setUserName("pašalinta pagal BDAR");
		}
		
		journalEntryDAO.saveAll(userJournalEntries);

	}
}
