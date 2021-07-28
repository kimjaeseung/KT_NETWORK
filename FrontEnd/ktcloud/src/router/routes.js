import DashboardLayout from "@/layout/dashboard/DashboardLayout.vue";
// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";

// Admin pages
import Dashboard from "@/pages/Dashboard.vue";
import UserProfile from "@/pages/UserProfile.vue";
import Notifications from "@/pages/Notifications.vue";
import createserver from "@/pages/createserver.vue";
import Maps from "@/pages/Maps.vue";
import Typography from "@/pages/Typography.vue";
import TableList from "@/pages/TableList.vue";
import main from "@/pages/Main.vue";
import loadingpage from "@/pages/loadingpage.vue";
import Network from "@/pages/Network.vue";
import Network2 from "@/pages/Network2.vue";
import success from "@/pages/success.vue";
import org from "@/pages/org.vue";
import Chartjs from "@/pages/Chartjs.vue";

const routes = [
  {
    mode: 'history',
    path: "/",
    component: DashboardLayout,
    redirect: "/Main",
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: Dashboard
      },
      {
        path: "stats",
        name: "stats",
        component: UserProfile
      },
      {
        path: "main",
        name: "main",
        component: main
      },
      {
        path: "notifications",
        name: "notifications",
        component: Notifications
      },
      {
        path: "loadingpage",
        name: "loadingpage",
        component: loadingpage
      },
      {
        path: "createserver",
        name: "createserver",
        component: createserver
      },
      {
        path: "maps",
        name: "maps",
        component: Maps
      },
      {
        path: "typography",
        name: "typography",
        component: Typography
      },
      {
        path: "table-list",
        name: "table-list",
        component: TableList
      },
      {
        path: "Network",
        name: "Network",
        component: Network
      },
      {
        path: "Network2",
        name: "Network2",
        component: Network2
      },
      {
        path: "success",
        name: "success",
        component: success
      },
      {
        path: "org",
        name: "org",
        component: org
      },
      {
        path: "Chartjs",
        name: "Chartjs",
        component: Chartjs
      },
    ]
  },
  { path: "*", component: NotFound }
];

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes;
