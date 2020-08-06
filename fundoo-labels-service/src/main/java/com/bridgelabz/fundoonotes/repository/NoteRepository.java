package com.bridgelabz.fundoonotes.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.entity.Note;


@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "insert into notes (color,title, description,is_archived,is_pinned,creation_time,user_id)"
			+ "values (?,?,?,?,?,?,?)", nativeQuery = true)
	void insertData(String color, String title, String description, boolean is_archived, boolean is_pinned,
			Timestamp creation_time, Long user_id);

	@Query(value = "select * from notes where note_id=?", nativeQuery = true)
	public Note findbyId(long note_Id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM notes WHERE note_id=?", nativeQuery = true)
	void delete(long noteId);

	@Modifying
	@Transactional
	@Query(value = "update notes set title=?,description=? where note_id=?", nativeQuery = true)
	void update(String title, String description, long noteId);

	
	@Modifying
	@Query(value = "update notes set title=?,description=? where note_id=?",nativeQuery = true)
	void updateData(String title,String description,Long note_id,Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "update notes set is_pinned=? where note_id=?", nativeQuery = true)
	void isPinned(boolean pin, long noteId);

	@Modifying
	@Transactional
	@Query(value = "update notes set is_archived=? where note_id=?", nativeQuery = true)
	void isArchived(boolean status, Long noteId);

	@Modifying
	@Transactional
	@Query(value = "update notes set is_trashed=? where note_id=?", nativeQuery = true)
	void isTrashed(boolean status, Long noteId);

	@Query(value = "select * from notes where note_id=?", nativeQuery = true)
	public List<Note> searchAllNoteByNoteId(long userId, long noteId);

	@Query(value = "select * from notes where title=?", nativeQuery = true)
	Note searchByTitle(String title);

	@Query(value = "select * from notes", nativeQuery = true)
	List<Note> findAllNotes(String token);
	
	
	@Query(value="select * from notes where note_id=?",nativeQuery = true)
	Note findByNoteId(Long noteId,Long userId);
	
}
