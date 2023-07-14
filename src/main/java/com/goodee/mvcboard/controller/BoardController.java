package com.goodee.mvcboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/board/removeBoard")
	public String removeBoard(HttpServletRequest request, Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.removeBoard(board, path);
		if(row == 1) {
			log.debug("\u001B[44m게시물 삭제 성공\u001B[0m");
		} else {
			log.debug("\u001B[44m게시물 삭제 실패\u001B[0m");
		}
		return "redirect:/board/boardList";
	}
	
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model, Board board, @RequestParam(name="boardfileNo", defaultValue = "0") int boardfileNo) {
		List<Map<String, Object>> localNameList = boardService.getLocalNameList();
		// 파일 번호를 받으면 삭제
		if(boardfileNo>0) {
			boardService.removeBordfileOne(boardfileNo);
		}
		List<Boardfile> fileList = boardService.getBoardfile(board.getBoardNo());
		
		model.addAttribute("localNameList", localNameList);
		model.addAttribute("fileList", fileList);
		return "/board/modifyBoard";
	}
	
	@PostMapping("board/modifyBoard")
	public String modifyBoard(HttpServletRequest request, Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.modifyBoard(board, path);
		if(row == 1) {
			log.debug("\u001B[44m게시물 수정 성공\u001B[0m");
		} else {
			log.debug("\u001B[44m게시물 수정 실패\u001B[0m");
		}
		return "redirect:/board/boardOne?boardNo="+board.getBoardNo();
	}
	
	@GetMapping("/board/addBoard")
	public String addBoard(Model model) {
		List<Map<String, Object>> localNameList = boardService.getLocalNameList();
		model.addAttribute("localNameList", localNameList);
		return "/board/addBoard";
	}
	
	@PostMapping("/board/addBoard")
	public String addBoard(HttpServletRequest request, Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.addBoard(board, path);
		if(row == 1) {
			log.debug("\u001B[44m게시물 입력 성공\u001B[0m");
		} else {
			log.debug("\u001B[44m게시물 입력 실패\u001B[0m");
		}
		return "redirect:/board/boardList";
	}
	
	@GetMapping("/board/boardOne")
	public String BoardOne(Model model, @RequestParam(name = "boardNo") int boardNo) {
		Map<String, Object> map = boardService.getBoardOne(boardNo);
		
		Board board = (Board)map.get("board");
		List<Boardfile> boardfiles = (List<Boardfile>)map.get("boardfiles");
		
		model.addAttribute("boardfiles", boardfiles);
		model.addAttribute("board", board);
		
		return "/board/boardOne";
	}
	
	@GetMapping("board/boardList")
	public String BoardList(Model model,@RequestParam(name="localName", required = false) String localName, @RequestParam(name = "currentPage", defaultValue = "1") int currentPage, @RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage) {
		// @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
		/*  int currentPage = 0
		 * 	if(request.getParameter("currentPage") == null) {
		 * 		currentPage = 1;
		 *	} else {
		 *		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		 *	}
		 */
		Map<String, Object> resultMap = boardService.getBoardList(localName, currentPage, rowPerPage);
		
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));	// request.setAttribute와 비슷한 역할
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		
		return "board/boardList";
	}
}
