<template>
  <div class="wrapper">
    <side-bar>
      <template slot="links">
        <sidebar-link to="/main" name="main" icon="ti-layout"/>
        <sidebar-link to="/createserver" name="Server" icon="ti-server"/>
        <sidebar-link to="/Network" name="Zone Network" icon="ti-vector"/>        
        <sidebar-link to="/Network2" name="Public IP Network" icon="ti-vector"/>
        <sidebar-link to="/dashboard_vm" name="Dashboard" icon="ti-eye"/>        
        <sidebar-link to="/check" name="Check" icon="ti-face-smile"/>        
        <sidebar-link to="/Chartjs" name="Chartjs" icon="ti-github"/>
        <!--<sidebar-link to="/table-list" name="firewall" icon="ti-view-list-alt"/>
        <sidebar-link to="/typography" name="topolo" icon="ti-text"/>-->
      </template> 
      <mobile-menu>
        <li class="nav-item">
          <a class="nav-link">
            <i class="ti-panel"></i>
            <p>Stats</p>
          </a>
        </li>
        <drop-down class="nav-item"
                   title="5 Notifications"
                   title-classes="nav-link"
                   icon="ti-bell">
          <a class="dropdown-item">Notification 1</a>
          <a class="dropdown-item">Notification 2</a>
          <a class="dropdown-item">Notification 3</a>
          <a class="dropdown-item">Notification 4</a>
          <a class="dropdown-item">Another notification</a>
        </drop-down>
        <li class="nav-item">
          <a class="nav-link">
            <i class="ti-settings"></i>
            <p>Settings</p>
          </a>
        </li>
        <li class="divider"></li>
      </mobile-menu>
    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar">

      </dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss">
</style>
<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
export default {
  components: {
    TopNavbar,
    ContentFooter,
    DashboardContent,
    MobileMenu
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    }
  }
};
</script>
