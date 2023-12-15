package xyz.funnyboy.process.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.model.process.Process;
import xyz.funnyboy.process.mapper.ProcessMapper;
import xyz.funnyboy.process.service.ProcessService;
import xyz.funnyboy.vo.process.ProcessQueryVO;
import xyz.funnyboy.vo.process.ProcessVO;

import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public IPage<ProcessVO> selectPage(Page<ProcessVO> pageParam, ProcessQueryVO processQueryVO) {
        return processMapper.selectPage(pageParam, processQueryVO);
    }

    /**
     * 通过 ZIP 部署
     *
     * @param deployPath 部署路径
     */
    @Override
    public void deployByZip(String deployPath) {
        // 定义zip输入流
        final InputStream inputStream = this.getClass()
                                            .getClassLoader()
                                            .getResourceAsStream(deployPath);
        final ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(inputStream));

        // 流程部署
        final Deployment deployment = repositoryService.createDeployment()
                                                       .addZipInputStream(zipInputStream)
                                                       .deploy();
        LOGGER.info("部署ID：" + deployment.getId());
        LOGGER.info("部署名称：" + deployment.getName());
    }
}
