package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Encrypt;
import cn.itcast.jx.util.MailUtil;
import cn.itcast.jx.util.Page;

public class UserServiceImpl implements UserService {
	
	private BaseDao baseDao;
	
	private SimpleMailMessage mailMessage;
	private JavaMailSenderImpl mailSender;
	
	private JmsTemplate queueTemplate;
	
	public void setQueueTemplate(JmsTemplate queueTemplate) {
		this.queueTemplate = queueTemplate;
	}

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<User> findPage(String hql, Page<User> page,
			Class<User> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}



	@Override
	public User get(Class<User> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}



	@Override
	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}



	@Override
	public void saveOrUpdate(final User entity) {
		if(StringUtils.isBlank(entity.getId())){
			//保存用户信息的时候，有什么注意点
			// 1 保存用户的同时需要保存用户扩展信息
			// 2 user和userInfo信息共主键，自定义主键，手动生成
			String id = UUID.randomUUID().toString();
			//给user
			entity.setId(id);
			//这个id还是userInfo的id
			entity.getUserInfo().setId(id);
			
			//随机密码
//			final String pwd = randomPwd();
			final String pwd = "123456";
			//加密
			String jiamipwd = Encrypt.md5(pwd, entity.getUserName());
			
			entity.setPassword(jiamipwd);
			
			//发送邮件
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						MailUtil.sendMail("账号信息", "欢迎加入百合网,您的账号是:"+entity.getUserName()+",您的密码是:"+pwd, entity.getUserInfo().getEmail());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();*/
			
			/************与Spring整合的方式发送电子邮件**************/
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					mailMessage.setSubject("账号信息");
					mailMessage.setText("欢迎加入百合网,您的账号是:"+entity.getUserName()+",您的密码是:"+pwd);
					mailMessage.setTo(entity.getUserInfo().getEmail());
					mailSender.send(mailMessage);
				}
			}).start();
			
			
			//发送短信通知用户密码信息
			queueTemplate.send("itheima18", new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					String value = entity.getUserInfo().getTelephone()+"&&&&&&&&&&"+"您的账号是:"+entity.getUserName()+",密码是:"+entity.getPassword();
					TextMessage message = session.createTextMessage(value);
					return message;
				}
			});
			
			
			//保存
			baseDao.saveOrUpdate(entity);
			
		}else{
			//修改:先查再改
			User user = baseDao.get(User.class, entity.getId());
			//修改用户的 部门、名字、状态
			user.setDept(entity.getDept());
			user.setUserName(entity.getUserName());
			user.setState(entity.getState());
			
			baseDao.saveOrUpdate(user);
			
		}
		
	}
	
	
	public String randomPwd(){
		String pwd = "";
		String values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVMXYZ0123456789.!@#$%^&*()_+";
		
		Random random = new Random();
		
		for(int i =0;i<6;i++){
			int index = random.nextInt(values.length());
			pwd+=values.charAt(index);
		}
		
		return pwd;
	}
	


	@Override
	public void deleteById(Class<User> entityClass, Serializable id) {
		/**
		 * 删除用户信息，注意点：
		 * 1 查出这个员工的下属
		 * 2 先查，存在就去删除，
		 * 
		 */
		//1 查找员工的直接下属
		List<User> list = baseDao.find("from User where userInfo.manager.id = ?", User.class, new Serializable[]{id});
		//将list集合中的下属的领导id置空
		if(list.size()>0){
			for(User user:list){
				user.getUserInfo().setManager(null);
				baseDao.saveOrUpdate(user);//快照更新
			}
		}
		//运行到此处，代表下属已经处理完了
		if(baseDao.get(entityClass, id)!=null){
			baseDao.deleteById(entityClass, id);
		}
	}



	@Override
	public void delete(Class<User> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
		
	}

	@Override
	public User findUserByUsername(String username) {
		String hql = "from User where userName = ?";
		List<User> list = baseDao.find(hql, User.class, new String[]{username});
		
		if(list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	
	
}
