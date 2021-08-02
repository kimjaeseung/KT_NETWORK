<template>
  <div>
    <button @click="showAlert">Show alert</button>
  </div>
</template>

<script>
export default {
  name: "alert-demo",
  data() {
    return {
      name: "",
      parent: "",
      categories: [
        { id: 1, name: "Entertainment" },
        { id: 2, name: "Technology" },
        { id: 3, name: "Sports" }
      ]
    };
  },
  methods: {
    async showAlert() {
      const { value } = await this.$swal({
        text: "edit child",
        html: `<input id="my-input" value="${
          this.name
        }" placeholder="Name" class="swal2-input">
          <select id="my-select" value="${
            this.parent
          }" name="parent" class="form-control">
            <option value="">nothing</option>
            ${this.categories.map(
              cat => `<option value="${cat.id}">${cat.name}</option>`
            )}
          </select>`,
        preConfirm: () => [
           document.getElementById("my-input").value,
           document.getElementById("my-select").value
        ],
        showCancelButton: true,
        confirmButtonText: "edit",
        cancelButtonText: "cancel",
        showCloseButton: true
      });

      console.log({ value });
    }
  }
};
</script>
