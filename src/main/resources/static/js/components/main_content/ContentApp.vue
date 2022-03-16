<template>
  <div class="main-container">
    <div class="sidenav">
      <Menu ref="menu" :username="username"/>
    </div>
    <div class="content">

    </div>
  </div>
</template>

<script>
import Menu from './menu/Menu.vue'

export default {
  components: {Menu},
  data(){
    return {
      username: ''
    }
  },
  mounted(){
    this.$api.getSelfData().then(
        (ok) => {
          const data = ok.data
          this.username = data.second_name + ' ' + data.first_name + ' ' + data.patronymic
        }
    )
    this.$refs.menu.addItem('Профиль')
  }
}
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: row;
  width: 75%;
  height: 95%;
  border-radius: 5px;
}

.sidenav {
  height: 100%;
  width: 300px;
  padding: 10px;
  background-color: #3F3F3F;
}

.content {
  padding-left: 20px;
  width: inherit;
  height: 100%;
  background-color: #EEEEEE;
}

</style>