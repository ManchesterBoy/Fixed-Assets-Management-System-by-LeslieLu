package com.example.sboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sboot.entity.Files;
import com.example.sboot.entity.User;
import com.example.sboot.mapper.FileMapper;
import com.example.sboot.mapper.UserMapper;
import com.example.sboot.service.IFileService;
import org.springframework.stereotype.Service;

/**
 * 服务实体类
 * @author LeslieLu
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {



}
