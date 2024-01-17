import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projector from './projector';
import ProjectorDetail from './projector-detail';
import ProjectorUpdate from './projector-update';
import ProjectorDeleteDialog from './projector-delete-dialog';

const ProjectorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projector />} />
    <Route path="new" element={<ProjectorUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectorDetail />} />
      <Route path="edit" element={<ProjectorUpdate />} />
      <Route path="delete" element={<ProjectorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectorRoutes;
