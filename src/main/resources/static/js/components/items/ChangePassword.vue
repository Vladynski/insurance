<template>
  <div class="block-column change-password-container" style="align-items: end">
    <div class="change-password-tag"></div>
    <div class="block-column change-password">
      <div class="block-row center block-row-items-margin change-password-content">
        <Input ref="oldPassword" placeholder="Старый пароль" type="password"
               style="width: 25%"/>
        <Input ref="newPassword" placeholder="Новый пароль" type="password"
               style="width: 25%"/>
        <Button :click="change" class="green-btn" style="font-size: 16px">Изменить</Button>
      </div>
    </div>
  </div>
</template>

<script>
import Input from "./Input.vue";
import Button from "./Button.vue";

export default {
  components: {Button, Input},
  methods: {
    change() {
      if (this.$refs.oldPassword.test() & this.$refs.newPassword.test()) {
        const oldPassword = this.$refs.oldPassword.getText()
        const newPassword = this.$refs.newPassword.getText()

        this.$api.editUserDataDto(
            oldPassword,
            undefined,
            undefined,
            newPassword).then(
            (ok) => {
              let placeholderText = this.$refs.newPassword.getPlaceholder();
              this.$refs.newPassword.setPlaceholder('Пароль изменён')
              this.$refs.newPassword.setText('')
              this.$refs.oldPassword.setText('')
              setTimeout(() => {
                this.$refs.newPassword.setPlaceholder(placeholderText)
              }, 3000)
            }, (err) => {
              if (err.response.status === 403) {
                this.$refs.oldPassword.showError('Неверный пароль')
                this.$refs.oldPassword.setText('')
              } else if (err.response.status === 400 && err.response.data.data.password)
                this.$refs.newPassword.showError('Неверный формат')
              else
                this.$refs.oldPassword.showError('Неизвестная ошибка')
            })
      }
    }
  }
}
</script>

<style scoped>
.change-password-container {
  position: absolute;
  bottom: -55px;
  width: 100%;
  height: 85px;
  transition-duration: 300ms;
}

.change-password-container:hover {
  bottom: 0;
}

.change-password-container:hover .change-password-tag {
  border-right-color: rgba(0, 0, 0, 0);
  border-bottom-color: rgba(0, 0, 0, 0);
}

.change-password {
  height: 55px;
  width: 100%;
  background-color: rgba(140, 140, 140, 0.5);
  bottom: 55px;
  justify-content: end;
}

.change-password-tag {
  transition-duration: 300ms;
  height: 30px;
  width: 30px;
  border: 15px solid transparent;
  border-right: 15px solid rgba(140, 140, 140, 0.5);
  border-bottom: 15px solid rgba(140, 140, 140, 0.5);
}

.change-password-content {
  height: 40px;
}
</style>