import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projector-type.reducer';

export const ProjectorTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectorTypeEntity = useAppSelector(state => state.projectorType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectorTypeDetailsHeading">Projector Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectorTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{projectorTypeEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{projectorTypeEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{projectorTypeEntity.obyekt ? projectorTypeEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/projector-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projector-type/${projectorTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectorTypeDetail;
