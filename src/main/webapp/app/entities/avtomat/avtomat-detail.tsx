import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './avtomat.reducer';

export const AvtomatDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const avtomatEntity = useAppSelector(state => state.avtomat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="avtomatDetailsHeading">Avtomat</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{avtomatEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{avtomatEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{avtomatEntity.model}</dd>
          <dt>
            <span id="group">Group</span>
          </dt>
          <dd>{avtomatEntity.group}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{avtomatEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{avtomatEntity.obyekt ? avtomatEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/avtomat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/avtomat/${avtomatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AvtomatDetail;
