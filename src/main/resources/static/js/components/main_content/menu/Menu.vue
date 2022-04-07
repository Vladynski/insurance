<template>
  <div>
    <div class="main-menu-title">
      <img src="favicon.png" class="logo" alt="logo"/>
      CarInsurance
    </div>
    <div style="padding: 10px; margin-top: -10px">
      <div class="menu-title" style="font-size: 22px; left: 0">{{ username }}</div>
      <MenuButton v-for="item in menuItems"
                  :customClick="item.selectAction" :menu-item="item"
                  :pre-select="preSelect"
                  :pictureName="item.pictureName"/>
    </div>
  </div>
</template>

<script>
import MenuButton from './MenuButton.vue'

export default {
  props: ['username'],
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
    addItem(itemName, pictureName, selectAction, unselectAction) {
      this.menuItems.push({
        itemName: itemName,
        selected: false,
        selectAction: selectAction,
        unselectAction: unselectAction,
        pictureName: pictureName
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
  position: relative;
}

.main-menu-title {
  font-family: Arial, serif;
  display: inline-block;
  border: none;
  color: #ffffff;
  text-align: center;
  font-size: 36px;
  position: relative;
  width: 100%;
  padding: 10px 10px 15px 10px;
  background: linear-gradient(#462446, #462446, #462446, #462446, #462446, #3F3F3F)
}

.logo {
  width: 36px;
  position: relative;
}
</style>