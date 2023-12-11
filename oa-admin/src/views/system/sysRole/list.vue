<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input
                v-model="searchObj.roleName"
                style="width: 100%"
                placeholder="角色名称"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display: flex">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            :loading="loading"
            @click="fetchData()"
          >搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData()">重置</el-button>
          <el-button type="success" icon="el-icon-plus" size="mini" @click="add()"></el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%; margin-top: 10px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" />

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="createTime" label="创建时间" width="160" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            title="修改"
            @click="edit(scope.row.id)"
          />
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            title="删除"
            @click="removeDataById(scope.row.id)"
          />
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center"
      layout="total,prev,pager,next,jumper"
      @current-change="fetchData"
    />
    <!-- 弹出层 -->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName" placeholder="角色名称"></el-input>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode" placeholder="角色编码"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" icon="el-icon-refresh-right" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" size="small" icon="el-icon-check" @click="saveOrUpdate()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import api from '@/api/system/sysRole'
export default {
  // 定义数据模型
  data() {
    return {
      list: [], // 列表数据
      total: 0, // 总记录数
      page: 1, // 当前页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询条件
      multipleSelection: [], // 批量删除选中的记录列表

      dialogVisible: false,
      sysRole: {},
      saveBtnDisabled: false,
    };
  },
  // 页面渲染成功前获取数据
  created() {
    this.fetchData()
  },
  // 定义方法
  methods: {
    // 获取角色列表
    fetchData(current = 1) {
      this.page = current
      api
        .getPageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          this.list = response.data.records
          this.total = response.data.total
        });
    },
    resetData() {
      console.log('删除查询表单')
      this.searchObj = {}
      this.fetchData()
    },
    // 删除角色
    removeDataById(id) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return api.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        this.$message.success(response.message || '删除成功')
      })
    },
    // 添加角色
    add() {
      this.dialogVisible = true
    },
    // 新增或修改
    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (!this.sysRole.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    // 新增角色
    saveData() {
      api.save(this.sysRole).then((response) => {
        this.$message.success(response.message || '添加成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    // 修改角色
    edit(id) {
      this.dialogVisible = true
      this.fetchDataById(id)
    },
    fetchDataById(id) {
      api.getById(id).then(response => {
        this.sysRole = response.data
      })
    },
    updateData() {
      api.updateById(this.sysRole).then(response => {
        this.$message.success(response.message || '修改成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    }
  },
};
</script>
