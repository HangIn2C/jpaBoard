package com.my.board.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.board.entity.Board;
import com.my.board.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	// 글 작성
	public void write(Board board, MultipartFile file) throws Exception { // file 생성시 Exception 처리
		// 저장 경로
		String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
		// 파일 랜덤 이름
		UUID uuid = UUID.randomUUID(); // 식별자
		// 파일 랜덤 이름 + 저장될 파일 이름
		String fileName = uuid + "_" + file.getOriginalFilename();
		
		File saveFile = new File(projectPath, fileName);
		
		file.transferTo(saveFile);
		
		board.setFilename(fileName); // 파일 이름
		
		board.setFilepath("/files/" + fileName); // 파일 이름 저장 경로
		
		boardRepository.save(board);
	}
	// 게시글 리스트 처리
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
		return boardRepository.findByTitleContaining(searchKeyword, pageable);
	}
	
	// 특정 게시글 불러오기
	public Board boardView(Integer id) {
		return boardRepository.findById(id).get();
	}
	
	// 특정 게시글 삭제
	public void boardDelete(Integer id) {
		boardRepository.deleteById(id);
	}
	
}
