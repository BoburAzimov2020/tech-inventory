import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './camera-brand.reducer';

export const CameraBrandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cameraBrandEntity = useAppSelector(state => state.cameraBrand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cameraBrandDetailsHeading">Camera Brand</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cameraBrandEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{cameraBrandEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{cameraBrandEntity.info}</dd>
        </dl>
        <Button tag={Link} to="/camera-brand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/camera-brand/${cameraBrandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CameraBrandDetail;
