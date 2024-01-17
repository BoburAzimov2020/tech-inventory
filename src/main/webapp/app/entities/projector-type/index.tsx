import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectorType from './projector-type';
import ProjectorTypeDetail from './projector-type-detail';
import ProjectorTypeUpdate from './projector-type-update';
import ProjectorTypeDeleteDialog from './projector-type-delete-dialog';

const ProjectorTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectorType />} />
    <Route path="new" element={<ProjectorTypeUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectorTypeDetail />} />
      <Route path="edit" element={<ProjectorTypeUpdate />} />
      <Route path="delete" element={<ProjectorTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectorTypeRoutes;
