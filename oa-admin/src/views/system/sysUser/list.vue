<template>
  <div class="app-container">

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
    </div>
    <br/>

    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关 键 字">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="用户名/姓名/手机号码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="createTimes"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 10px;width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" :loading="loading" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="100"/>
      <el-table-column prop="name" label="姓名" width="70"/>
      <el-table-column prop="phone" label="手机" width="120"/>
      <el-table-column prop="postName" label="岗位" width="100"/>
      <el-table-column prop="deptName" label="部门" width="100"/>
      <el-table-column label="所属角色" width="130">
        <template slot-scope="scope">
          <span v-for="item in scope.row.roleList" :key="item.id" style="margin-right: 10px;">{{ item.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status === 1"
            @change="switchStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160"/>

      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
      />

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
      <el-form ref="dataForm" :model="sysUser"  label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username"/>
        </el-form-item>
        <el-form-item v-if="!sysUser.id" label="密码" prop="password">
          <el-input v-model="sysUser.password" type="password"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sysUser.name"/>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/system/sysUser'

const defaultForm = {
 id: '', 
 username: '', 
 password: '', 
 name: '', 
 phone: '',
 status: 1 
}

export default {
    data() {
        return {
            loading: false,
            listLoading: true,
            list: null,
            total: 0,
            page: 1,
            limit: 10,
            searchObj: {},

            createTimes: [],

            dialogVisible: false,
            sysUser: defaultForm,
            saveBtnDisabled: false,
        }
    },

    created() {
        console.log('list created...');
        this.fetchData()
    },

    mounted() {
        console.log('list mounted...');
    },

    methods: {
        changeSize(size) {
            console.log(size)
            this.limit = size
            this.fetchData(1)
        },
        // 查询用户列表
        fetchData(page = 1) {
            this.page = page

            // 判断是否选择操作时间
            if (this.createTimes && this.createTimes.length == 2) {
                this.searchObj.createTimeBegin = this.createTimes[0]
                this.searchObj.createTimeEnd = this.createTimes[1]
            }

            // 调用 Api 获取用户数据
            api.getPageList(this.page, this.limit, this.searchObj).then(
                response => {
                    this.list = response.data.records
                    this.total = response.data.total
                    this.listLoading = false
                }
            )
        },
        // 重置表单
        resetData() {
            this.searchObj = {}
            this.createTimes = []
            this.fetchData()
        },
        // 删除用户
        removeDataById(id) {
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                return api.removeById(id)
            }).then((response) => {
                this.$message.success(response.message || '删除成功')
                this.fetchData(this.page)
            }).catch(()=>{
                this.$message.info(response.message || '删除失败')
            })
        },
        // 添加用户
        add() {
            this.dialogVisible = true
            this.sysUser = Object.assign({}, defaultForm)
        },
        // 修改用户
        edit(id) {
            this.dialogVisible = true
            this.fetchDataById(id)
        },
        fetchDataById(id) {
            api.getById(id).then(response => {
                this.sysUser = response.data
            })
        },
        // 保存或更新
        saveOrUpdate() {
            this.$refs.dataForm.validate(valid => {
                if (!valid) {
                    this.$message.info('数据校验失败')
                    return
                }
                this.saveBtnDisabled = true
                if (!this.sysUser.id) {
                    this.saveData()
                } else {
                    this.updateData()
                }
            })
        },
        saveData() {
            api.save(this.sysUser).then(response => {
                this.$message.success(response.message || '保存成功')
                this.dialogVisible = false
                this.fetchData(this.page)
            })
        },
        updateData() {
            api.updateById(this.sysUser).then(response => {
                this.$message.success(response.message || '更新成功')
                this.dialogVisible = false
                this.fetchData(this.page)
            })
        }
    },
}

</script>