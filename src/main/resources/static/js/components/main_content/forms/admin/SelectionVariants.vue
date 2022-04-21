<template>
  <div class="block-column block-column-items-margin" style="justify-content: flex-start">
    <div class="block-column" style="height: auto;">
      <Button class="btn green-btn d-btn-max-size" :click="addGroup">Добавить новую группу</Button>
      <InfoFrame ref="infoFrame"/>
    </div>
    <div class="block-column block-items-margin def-block-vertical-scrolling"
         style="justify-content: flex-start">
      <template v-for="(selection, index) in selections" :key="selection.id">
        <SelectionCard :selection="selection" :groupUpdated="groupUpdated"/>
        <hr v-if="index !== selections.length - 1" class="separator">
      </template>
    </div>
  </div>
</template>

<script>
import Button from "../../../items/Button.vue";
import SelectionCard from "../admin/forms/selection/SelectionCard.vue";
import InfoFrame from "../../../items/InfoFrame.vue";

export default {
  components: {InfoFrame, Button, SelectionCard},
  data() {
    return {
      selections: undefined
    }
  },
  methods: {
    addGroup() {
      if (!this.selections.find(el => el.id === 0)) {
        this.selections.push({
          id: 0,
          name: '',
          variants: []
        })
      }
    },
    groupUpdated(id, group) {
      const find = this.selections.find(el => el.id === id);
      if (group.deleted) {
        this.selections.splice(this.selections.indexOf(find), 1)
      }
      find.id = group.id
      find.name = group.name
    }
  },
  beforeMount() {
    this.$adminApi.getSelectionData().then(
        (ok) => {
          this.selections = ok.data.selections
          console.log(this.selections);
        },
        (err) => {
          this.$api.errorHandler(err, () => this.$refs.infoFrame)
        }
    )
  }
}
</script>

<style scoped>

</style>