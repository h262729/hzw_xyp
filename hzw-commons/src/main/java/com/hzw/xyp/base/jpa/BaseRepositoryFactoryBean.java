package com.hzw.xyp.base.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 工厂类 - 用于注册自定义的jap接口
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I>{

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    /**
     * 私有内部类
     */
    private class BaseRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory{
        private final EntityManager entityManager;

        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        /**
         * 设置具体实现类是 BaseRepositoryImpl
         */
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), entityManager);
        }

        /**
         * 设置具体实现类的class
         */
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}
