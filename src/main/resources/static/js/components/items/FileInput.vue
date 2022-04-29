<template>
  <div class="choose-file-content">
    <input :id="id" type="file" :accept="accept" :onchange="change" hidden style="position: absolute"/>
    <label :for="id" class="choose-file-label">Choose File</label>
    <div class="choose-file-info " :class="chose?'file-chose':''">
      <div ref="fileName"  style="overflow:hidden; width: 100%; height: 100%; display: flex; align-items: center; transition-duration: 300ms" :title="filename">
        {{ this.filename ? this.filename : 'Файл не выбран' }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['id', 'choose', 'accept'],
  data() {
    return {
      chose: false,
      filename: undefined
    }
  },
  methods: {
    change() {
      this.chose = true
      const file = document.querySelector('#passportPhoto').files[0]
      this.filename = file.name
      this.choose(file)
    }
  }
}
</script>

<style scoped>
.fileName {
  display: inline-block;
  height: 100%;
}

.choose-file-content {
  display: flex;
  align-items: center;
  width: 100%;
  height: 35px;
}
.choose-file-label {
  display: flex;
  align-items: center;
  background-color: indigo;
  color: white;
  padding: 5px 10px 5px 10px;
  font-size: 16px;
  font-family: sans-serif;
  border-radius: 0.3rem;
  cursor: pointer;
  text-align: center;
  height: 100%;
  z-index: 1;
}

.choose-file-info {
  display: flex;
  align-items: center;
  margin-left: 0.3rem;
  font-family: sans-serif;
  background-color: #C5DDE8;
  overflow: visible;
  position: relative;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  padding-right: 5px;
  height: 100%;
  transition-duration: 300ms;
}

.choose-file-info {
  max-width: 200px;
}

.choose-file-info:hover{
  max-width: 100%;
}

.choose-file-info:before {
  content: '';
  position: absolute;
  left: -10px;
  width: 10px;
  height: 100%;
  background-color: #C5DDE8;
}

.file-chose {
  background-color: #a6e8a6;
}

.file-chose:before {
  background-color: #a6e8a6;
}
</style>