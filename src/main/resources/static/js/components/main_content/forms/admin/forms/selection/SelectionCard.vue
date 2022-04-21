<template>
  <SlidingBlock :title="'ID: ' + id + ' Группа ' + selection.name" positionDefault="true">
    <Input ref="name" placeholder="Название" :value="selection.name" :changeListener="true" inputClass="d-input-size"/>
    <div class="block-column variants">
      <SelectionVariant :ref="(el) => addSelectionVariantForm(variant.id, el)"
                        v-for="variant in variants"
                        :key="variant.id"
                        :variant="variant"/>
    </div>
    <div class="block-row block-row-items-margin">
      <Button class="btn blue-btn d-btn-max-size" :click="addVariant">Добавить вариант</Button>
      <Button class="btn green-btn d-btn-max-size" :click="update">Применить изменения</Button>
      <Button class="btn red-btn d-btn-max-size" :click="deleteGroup">Удалить группу</Button>
    </div>
  </SlidingBlock>
</template>

<script>
import SlidingBlock from "../../../../../items/SlidingBlock.vue";
import Input from "../../../../../items/Input.vue";
import SelectionVariant from "./SelectionVariant.vue";
import Button from "../../../../../items/Button.vue";

export default {
  components: {Button, SelectionVariant, Input, SlidingBlock},
  props: ['selection', 'groupUpdated'],
  data() {
    return {
      id: this.selection.id,
      variantList: [],
      nameList: [],
      variants: this.selection.variants,
    }
  },
  methods: {
    update() {
      this.updateName()
      for (let variant of this.variantList) {
        let updateObject = undefined
        if (variant && (updateObject = variant.getUpdateObject())) {
          if (updateObject.deleteAction) {
            this.$adminApi.deleteSelectionVariant(updateObject.id).then((ok) => {
              this.updateList()
            }, (err) => {
              console.log(err.response);
            })
          } else {
            this.$adminApi.updateSelectionVariant(updateObject.id, this.id, updateObject.name, updateObject.coefficient).then(
                (ok) => {
                  this.updateList()
                }, (err) => {
                  //FIXME
                  console.log(err.response);
                }
            )
          }
        }
      }
    },
    addSelectionVariantForm(varName, el) {
      if (this.nameList.indexOf(varName) === -1) {
        this.nameList.push(varName)
        this.variantList.push(el)
      }
    },
    updateList() {
      this.$adminApi.getGroup(this.id).then(
          (ok) => {
            while (this.variants.length !== 0)
              this.variants.shift()
            this.variantList = []
            this.nameList = []
            ok.data.variants.forEach(el => {
              this.variants.push(el)
            })
          }, (err) => {
            console.log(err.response);
          }
      )
    },
    addVariant() {
      if (!this.variants.find(el => el.id === 0)) {
        this.variants.push({
          id: 0,
          name: '',
          coefficient: 0
        })
      }
    },
    updateName() {
      if (this.$refs.name.test()) {
        const newValue = this.$refs.name.getNewValue();
        if (newValue) {
          this.$adminApi.updateGroup(this.selection.id, newValue).then(
              (ok) => {
                this.groupUpdated(this.id, {
                  id: this.id,
                  name: this.$refs.name.getText()
                })
                this.id = ok.data
                this.$refs.name.updateStartValue()
              }, (err) => {
                console.log(err.response) //fixme
              }
          )
        }
      }
    },
    deleteGroup() {
      this.$adminApi.deleteGroup(this.id).then(
          (ok) => {
            this.groupUpdated(this.id, {deleted:true})
          }, (err) => {
            console.log(err.response)
          }
      )
    }
  },
  beforeMount() {
    this.variantList = []
  }
}
</script>

<style scoped>
.variants {
  margin-top: 15px;
}
</style>