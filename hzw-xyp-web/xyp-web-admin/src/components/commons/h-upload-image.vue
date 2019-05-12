<template>
    <!-- 图片上传组件 -->
  <div class="upload-image">
    <el-upload
      ref="imageUpload"
      :class="{disabled:uploadDisabled}"
      :action="uploadImageUrl"
      accept="image"
      list-type="picture-card"
      :data="uploadImageData"
      :file-list="currentImageList"
      :limit="limit"
      :on-preview="viewImage"
      :on-remove="removeImage"
      :on-error="uploadError"
      :on-exceed="exceed"
      multiple
    >
      <i class="el-icon-plus"></i>
      <div slot="tip" class="el-upload__tip">最多可上传{{limit}}张图片</div>
    </el-upload>

    <!-- 图片查看弹窗 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "h-upload-image",
    data(){
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        currentImageList : [], // 上传图片集合（路径改为全路径），用来组件展示的
        getImageBaseUrl : this.GLOBAL.BASE_URL, // 图片获取基础路径
        uploadImageUrl: this.url || this.GLOBAL.BASE_IMAGE_UPLOAD_URL, // 上传请求路径
        uploadImageData: this.uploadData || {target : this.target, targetId : this.targetId, mode : this.mode}, // 上传附加参数
      }
    },
    created(){
      // 路径拼接，生成图片展示的全路径
      for(let imageData of this.imagesList){
        imageData["imageUrl"] = imageData["url"];
        if(imageData["mode"] == 1){ // 本地存储模式
          imageData["url"] = this.getImageBaseUrl + imageData["url"];
        }
        this.currentImageList.push(imageData);
      }
    },
    methods: {
      getUploadFiles(){ // 获取所有的上传成功图片
        console.log("获取上传成功图片", this.$refs.imageUpload.uploadFiles);
        let images = [];
        for(let uploadFile of this.$refs.imageUpload.uploadFiles){
          let imageData = null;
          if(uploadFile.response){ // 最新上传的
            let responseData = uploadFile.response["imgData"];
            if(uploadFile.status == "success" && responseData && responseData.url){
              imageData = {name: responseData.name, mode: responseData.mode, url: responseData.url};
            }
          }else{ // 原有的数据
            imageData = {name: uploadFile.name, mode: uploadFile.mode, url: uploadFile.imageUrl};
          }
          if(imageData && imageData.url){
            images.push(imageData);
          }
        }
        console.log("获取上传成功图片的可存储数据", images);
        return images;
      },
      viewImage(file) { // 点击查看图片
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      removeImage(file, fileList){  // 图片移除，这里主要是针对原有图片数组的修改
        this.currentImageList = this.currentImageList.filter((imageData) => {return imageData.uid != file.uid});
      },
      uploadError(err, file, fileList){ // 上传失败
        console.log("上传失败", err.message);
        let errorData = JSON.parse(err.message);
        if(errorData["code"] == 330){
          this.FUNCS.error(errorData["message"] || "图片上传失败");
        }else{
          this.FUNCS.error("图片上传失败");
        }
      },
      exceed(file, fileList){ // 上传数量超出限制
        this.FUNCS.warning("上传数量过多，最多可上传" + this.limit + "张图片！");
      }
    },
    computed:{
      uploadDisabled : function(){
        let flag = false;
        let _this = this;
        this.$nextTick(() => {
          flag = _this.$refs.imageUpload.uploadFiles.length >= this.limit;
        });
        console.log("计算属性", flag);
        return flag;
      }
    },
    props: {
      target: {type:String, default: "temp"}, // 上传目标对象
      targetId: {type:Number, default: 0},  // 上传目标对象id
      mode: {type:Number, default: 1},  // 文件存储模式，默认1>本地存储
      limit: {type: Number, default: 10},  // 数量限制
      uploadData : Object, // 上传附加参数
      url : String, // 上传存储连接
      imagesList: {type: Array, default: () => []},  // 图片列表
    }
  }
</script>

<style>
  .disabled .el-upload--picture-card {
    display: none;
  }
</style>
