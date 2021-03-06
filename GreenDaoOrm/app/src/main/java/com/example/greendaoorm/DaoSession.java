package com.example.greendaoorm;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.greendaoorm.Diary;

import com.example.greendaoorm.DiaryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig diaryDaoConfig;

    private final DiaryDao diaryDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        diaryDaoConfig = daoConfigMap.get(DiaryDao.class).clone();
        diaryDaoConfig.initIdentityScope(type);

        diaryDao = new DiaryDao(diaryDaoConfig, this);

        registerDao(Diary.class, diaryDao);
    }
    
    public void clear() {
        diaryDaoConfig.clearIdentityScope();
    }

    public DiaryDao getDiaryDao() {
        return diaryDao;
    }

}
