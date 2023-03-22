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
	
	
	public int anJoin(MemberDTO dto) {
		
		
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
				System.out.println(state + "���Լ���");				
			} else {
				System.out.println(state + "���Խ���");
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

		//8. ���ϴ� ���� ���Ͻ�Ų��
		return state;
		
	}
	
	// �α��� �� �� �α����� ��� �����͸� ������
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

	// ��� ����� ������ ������
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
				System.out.println(state + "��������");				
			} else {
				System.out.println(state + "��������");
			}
			
			// ������ �ٽ� ��� ���̺� ������ �����´�
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
//			System.out.println("adtosũ��" + adtos.size());
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
//				System.out.println(state + "���Լ���");				
//			} else {
//				System.out.println(state + "���Խ���");
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
//			// ���̵�� �����Ҽ� ����			
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
//				System.out.println("����1����");
//				
//			} else {
//				System.out.println("����1����");
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
//			// ���̵�� �����Ҽ� ����			
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
//				System.out.println("����2����");
//				
//			} else {
//				System.out.println("����2����");
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
//				System.out.println("��������");				
//			} else {
//				System.out.println("��������");
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
