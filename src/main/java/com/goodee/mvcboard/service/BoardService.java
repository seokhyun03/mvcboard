package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;	
	@Autowired
	private BoardfileMapper boardfileMapper;
	static final String BLUE = "\u001B[44m";
	static final String RESET = "\u001B[0m";
	
	public int removeBoard(Board board, String path) {
		List<Boardfile> list = boardfileMapper.selectBoardfile(board.getBoardNo());
		
		for(Boardfile bf : list) {
			File f = new File(path+bf.getSaveFilename());
			if(f.exists()) {
				f.delete();
			}
		}
		int cnt = boardfileMapper.selectBoardfileCount(board.getBoardNo());
		int row = boardfileMapper.deleteBoardfile(board.getBoardNo());
		
		if(cnt == row) {
			row = boardMapper.deleteBoard(board);
		}
		
		return row;
	}
	
	public int removeBordfileOne(Boardfile boardfile) {
		return boardfileMapper.deleteBoardfileOne(boardfile.getBoardNo());
	}
	
	public int modifyBoard(Board board) {
		int row = boardMapper.updateBoard(board);
		return boardMapper.updateBoard(board);
	}
	
	public List<Map<String, Object>> getLocalNameList(){
		return boardMapper.selectLocalNameList();
	}
	
	public List<Boardfile> getBoardfile(int boardNo){
		return boardfileMapper.selectBoardfile(boardNo);
	}
	
	public int addBoard(Board board, String path) {
		int row = boardMapper.insertBoard(board);
		log.debug(BLUE + row + RESET);
		// board 추가 성공
		if(row == 1) {
			int boardNo = board.getBoardNo();
			List<MultipartFile> fileList = board.getMultipartFile();	
			log.debug(BLUE + fileList.size() + RESET);
			// 첨부된 파일이 한개 이상 일 때
			if(fileList != null && fileList.size() > 0) {
				for(MultipartFile mf : fileList) {
					if(mf.getSize() > 0) {
						// 확장자
						String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
						// 저장시 사용할 파일 이름
						String saveFilename = UUID.randomUUID().toString().replace("-", "") + ext;
						
						Boardfile bf = new Boardfile();
						bf.setBoardNo(boardNo);	// 부모 키 값
						bf.setOriginFilename(mf.getOriginalFilename());	// 원본파일이름
						bf.setSaveFilename(saveFilename);	// 저장파일이름
						bf.setFiletype(mf.getContentType());	// 파일 타입(MIME)
						bf.setFilesize(mf.getSize());	// 파일 사이즈
						// boardfile 테이블에 데이터 저장
						boardfileMapper.insertBoardfile(bf);
						// 파일 저장
						// path위치에 저장파일이름으로 빈파일을 생성
						File f = new File(path+bf.getSaveFilename());
						// 빈파일에 첨부된파일의 스트림을 주입한다
						try {
							mf.transferTo(f);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							// 트랜잭션 작동을 위해 예외(try/catch를 강요하지 않는 예외: RuntimeException) 발생이 필요
							throw new RuntimeException();
						}
					}
				}
			}
		}
		
		return row;
	}
	
	public Map<String, Object> getBoardOne(int boardNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("board", boardMapper.selectBoardOne(boardNo));
		map.put("boardfiles", boardfileMapper.selectBoardfile(boardNo));
		return map;
	}
	
	public Map<String, Object> getBoardList(String localName, int currentPage, int rowPerPage){
		// service layer 역할 1 : controller가 넘겨준 값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage-1)*rowPerPage;

		List<Map<String, Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("localName", localName);
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
		// service layer 역할 2 : dao에게 반환받은 값을 가공하여 controller에 반환
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = boardCount / rowPerPage;
		if(boardCount % rowPerPage != 0) {
			lastPage += 1;
		}
		
		// 반환값들을 Map에 저장하여 반환
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		return resultMap;
	}
}
