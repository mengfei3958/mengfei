package com.example.demo.service.Impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.StudentMapper;
import com.example.demo.entity.Bank;
import com.example.demo.entity.Student1;
//import com.example.demo.service.StudentPlusMapper;
import com.example.demo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService, InitializingBean ,DisposableBean {

	@Autowired
	private StudentDao studentDao;
	
//	private StudentPlusMapper studentPlusMapper;
	
	@Override
	@Transactional
	public List<Student1> getAll() {
		// TODO Auto-generated method stub
		
		return (List<Student1>) studentDao.findAll();
	}

	@Override
	@Transactional
	public List<Student1> findAllByName(String name) {
		Specification querySpecifi = new Specification<Student1>() {			
			@Override
			public Predicate toPredicate(Root<Student1> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Path<String> exp1 = root.get("name");
				return cb.like(exp1, "%"+name+"%");
			}
		};
//		List<Student> list = findAllJoin();
//		if (list != null) {
//			for (Student student : list) {
//				System.out.println(student);
//			}
//		}
		return studentDao.findAll(querySpecifi);
	}

	@Override
	@Transactional
	public List<Student1> findAllJoin() {
		// TODO Auto-generated method stub
		Specification querySpecifi = new Specification<Student1>() {
			
			@Override
			public Predicate toPredicate(Root<Student1> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stubs				
				Join<Student1, Bank> join = root.join("bank",JoinType.INNER);
				Path<String> param = join.get("b_id");
				return cb.equal(param, "1");
			}
		};
		return studentDao.findAll(querySpecifi);
	}
	
	@PostConstruct  
    public void initMethod() throws Exception {  
        System.out.println("initMethod 被执行");  
    }  
    @PreDestroy  
    public void destroyMethod() throws Exception {  
        System.out.println("destroyMethod 被执行");  
    }

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bean 销毁了。。。");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bean 开始初始化啦。。");
	}  

}
