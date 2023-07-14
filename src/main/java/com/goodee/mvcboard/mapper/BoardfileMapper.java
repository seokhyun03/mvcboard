package com.goodee.mvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	int insertBoardfile(Boardfile boardfile);
	
	int deleteBoardfile(int boardNo);
	
	int deleteBoardfileOne(int boardfileNo);
	
	List<Boardfile> selectBoardfile(int boardNo);
	
	int selectBoardfileCount(int boardNo);
}
