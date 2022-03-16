<template>
  <div>
    <div class="menu-title">Меню</div>
    <div class="menu-title" style="font-size: 22px">{{ username }}</div>
    <MenuButton v-for="item in menuItems" :customClick="menuItems.selectAction" :menu-item="item" :pre-select="preSelect"/>
  </div>
</template>

<script>
import MenuButton from './MenuButton.vue'

export default {
  props:['username'],
  data() {
    return {
      menuItems: []
    }
  },
  components: {
    MenuButton
  },
  methods: {
    preSelect() {
      this.menuItems.forEach(el => {
        if (el.selected) {
          el.selected = false
          el.unselectAction()
        }
      })
    },
    addItem(itemName, selectAction, unselectAction) {
      this.menuItems.push({
        itemName: itemName,
        selected: false,
        selectAction: selectAction,
        unselectAction: unselectAction
      })
    }
  }
}
</script>

<style>
.menu-title {
  font-family: Arial, serif;
  display: inline-block;
  border: none;
  color: #ffffff;
  text-align: center;
  font-size: 36px;
  width: 100%;
  margin: 5px 0 10px 0;
}
</style>