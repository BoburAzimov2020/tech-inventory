import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rozetka.reducer';

export const RozetkaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rozetkaEntity = useAppSelector(state => state.rozetka.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rozetkaDetailsHeading">Rozetka</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rozetkaEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{rozetkaEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{rozetkaEntity.model}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{rozetkaEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{rozetkaEntity.obyekt ? rozetkaEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rozetka" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rozetka/${rozetkaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RozetkaDetail;
