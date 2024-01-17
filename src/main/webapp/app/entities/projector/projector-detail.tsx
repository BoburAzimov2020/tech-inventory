import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projector.reducer';

export const ProjectorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectorEntity = useAppSelector(state => state.projector.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectorDetailsHeading">Projector</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectorEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{projectorEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{projectorEntity.model}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{projectorEntity.info}</dd>
          <dt>Projector Type</dt>
          <dd>{projectorEntity.projectorType ? projectorEntity.projectorType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/projector" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projector/${projectorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectorDetail;