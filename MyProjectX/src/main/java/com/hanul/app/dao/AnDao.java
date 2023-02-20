package com.hanul.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.hanul.app.dto.MemberDTO;


public class AnDao {
	DataSource dataSource;

	public AnDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/teamAll");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}
	
	//7. 회원가입 : command에서 넘어온 값을 받는다
	public int anJoin(MemberDTO dto) {
		
		// 데이터베이스와 연결하여 원하는 결과물을 얻는다
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into member(id, pw, name, phone, address, profile) " + 
			               "values('" + dto.getId() + "', '" + dto.getPw() + "', '" + dto.getName() + "', '" + 
					        			dto.getPhone() + "', '" + dto.getAddress() + "', '" + dto.getProfile() + "' )";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {			
			System.out.println(e.getMessage());
		} finally {
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		//8. 원하는 값을 리턴시킨다
		return state;
		
	}
	
	// 로그인 한 후 로그인한 멤버 데이터를 보낸다
	public MemberDTO anLogin(String idin, String pwin) {

    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from member" 
							+ " where id = '" + idin + "' and pw = '" + pwin + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String phone = resultSet.getString("phone");
				String address = resultSet.getString("address"); 
				String profile = resultSet.getString("profile"); 

				adto = new MemberDTO(id, name, phone, address, profile);							
			}	
			
			System.out.println("MemberDTO id : " + adto.getId());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}

	// 모든 멤버의 정보를 보낸다
	public ArrayList<MemberDTO> selectMembers() {
		
		ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from member"	;	
							
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String phone = resultSet.getString("phone");
				String address = resultSet.getString("address"); 
				String profile = resultSet.getString("profile"); 

				dtos.add(new MemberDTO(id, name, phone, address, profile));							
			}	
			
			System.out.println("dtos size : " + dtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return dtos;
		
	}

	public ArrayList<MemberDTO> deleteMember(String idin) {
		ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "delete from member where id='" + idin + "' ";	
							
			prepareStatement = connection.prepareStatement(query);
			int state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삭제성공");				
			} else {
				System.out.println(state + "삭제실패");
			}
			
			// 삭제후 다시 멤버 테이블 내용을 가져온다
			query = "select * from member"	;	
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String phone = resultSet.getString("phone");
				String address = resultSet.getString("address"); 
				String profile = resultSet.getString("profile"); 

				dtos.add(new MemberDTO(id, name, phone, address, profile));							
			}	
			
			System.out.println("dtos size : " + dtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return dtos;
	}
	
//public ArrayList<MyItemDTO> anSelectMulti() {		
//		
//		ArrayList<MyItemDTO> adtos = new ArrayList<MyItemDTO>();
//		Connection connection = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;		
//		
//		try {
//			connection = dataSource.getConnection();
//			String query = "select id, name, hire_date, image_path "					
//							+ " from android" 
//							+ " order by id desc";
//			prepareStatement = connection.prepareStatement(query);
//			resultSet = prepareStatement.executeQuery();
//			
//			while (resultSet.next()) {
//				int id = resultSet.getInt("id");
//				String name = resultSet.getString("name");
//				Date date = resultSet.getDate("hire_date"); 
//				String imagePath = resultSet.getString("image_path"); 
//
//				MyItemDTO adto = new MyItemDTO(id, name, date, imagePath);
//				adtos.add(adto);			
//			}	
//			
//			System.out.println("adtos크기" + adtos.size());
//			
//		} catch (Exception e) {
//			
//			System.out.println(e.getMessage());
//		} finally {
//			try {			
//				
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (prepareStatement != null) {
//					prepareStatement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}	
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//
//			}
//		}
//
//		return adtos;
//
//	}
//	
//
//	public int anInsertMulti(int id, String name, String date, String dbImgPath) {
//		
//		Connection connection = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;
//				
//		int state = -1;
//	
//		try {			
//			// 
//			connection = dataSource.getConnection();
//			String query = "insert into android(id, name, hire_date, image_path) " + "values(" + id + ",'" 
//							+ name + "'," + "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "' )";
//	
//			prepareStatement = connection.prepareStatement(query);
//			state = prepareStatement.executeUpdate();
//			
//			if (state > 0) {
//				System.out.println(state + "삽입성공");				
//			} else {
//				System.out.println(state + "삽입실패");
//			}
//	
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		} finally {
//			try {
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (prepareStatement != null) {
//					prepareStatement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//	
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//	
//		}
//	
//		return state;
//	
//	}
//	
//public int anUpdateMulti(int id, String name, String date, String dbImgPath) {
//		
//		Connection connection = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;
//		
//		int state = -1;
//	
//		try {			
//			// 아이디는 수정할수 없음			
//			connection = dataSource.getConnection();
//			String query = "update android set " 			             
//		             + " name = '" + name + "' "
//		             + ", hire_date = '" + date + "' "
//		             + ", image_path = '" + dbImgPath + "' "
//					 + " where id = " + id ;
//			
//			prepareStatement = connection.prepareStatement(query);
//			state = prepareStatement.executeUpdate();
//	
//			if (state > 0) {
//				System.out.println("수정1성공");
//				
//			} else {
//				System.out.println("수정1실패");
//			}
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (prepareStatement != null) {
//					prepareStatement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//	
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//	
//			}
//		}
//	
//		return state;
//	
//	}
//	
//	public int anUpdateMultiNo(int id, String name, String date) {
//		
//		Connection connection = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;
//		
//		int state = -1;
//	
//		try {			
//			// 아이디는 수정할수 없음			
//			connection = dataSource.getConnection();
//			String query = "update android set " 			             
//		             + " name = '" + name + "' "
//		             + ", hire_date = '" + date + "' "		             
//					 + " where id = " + id ;
//			
//			prepareStatement = connection.prepareStatement(query);
//			state = prepareStatement.executeUpdate();
//	
//			if (state > 0) {
//				System.out.println("수정2성공");
//				
//			} else {
//				System.out.println("수정2실패");
//			}
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (prepareStatement != null) {
//					prepareStatement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//	
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//	
//			}
//		}
//	
//		return state;
//	}
//	
//	public int anDeleteMulti(int id) {
//		
//		Connection connection = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;
//		
//		int state = -1;
//
//		try {
//			connection = dataSource.getConnection();
//			String query = "delete from android where id=" + id;
//			
//			System.out.println(id);
//
//			prepareStatement = connection.prepareStatement(query);
//			state = prepareStatement.executeUpdate();
//
//			if (state > 0) {
//				System.out.println("삭제성공");				
//			} else {
//				System.out.println("삭제실패");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (prepareStatement != null) {
//					prepareStatement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		return state;
//
//	}

	
}
